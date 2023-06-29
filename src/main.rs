mod buffered;
mod hamming;
mod huffman;
mod util;

slint::include_modules!();

use buffered::reader::read_u64;
use hamming::{init_masks, MAX_BLOCK_SIZE, MAX_EXPONENT};
use huffman::TableEntry;
use rfd::FileDialog;
use slint::{Model, SharedString, VecModel};
use std::{
    fs::File,
    io::{BufReader, Read},
    rc::Rc,
};
use util::string::Extention;

fn main() {
    let masks = init_masks();
    let main_window = MainWindow::new().unwrap();

    let default_errors: Vec<SharedString> = main_window.get_errors().iter().collect();
    let errors = Rc::new(slint::VecModel::from(default_errors));
    main_window.set_errors(errors.clone().into());

    let default_in_progress: Vec<bool> = main_window.get_inProgress().iter().collect();
    let in_progress = Rc::new(slint::VecModel::from(default_in_progress));
    main_window.set_inProgress(in_progress.clone().into());

    let default_orig: Vec<SharedString> = main_window.get_orig_text().iter().collect();
    let orig_text = Rc::new(slint::VecModel::from(default_orig));
    main_window.set_orig_text(orig_text.clone().into());

    let default_proc: Vec<SharedString> = main_window.get_proc_text().iter().collect();
    let proc_text = Rc::new(slint::VecModel::from(default_proc));
    main_window.set_proc_text(proc_text.clone().into());

    let default_stat: Vec<SharedString> = main_window.get_stat().iter().collect();
    let stat = Rc::new(slint::VecModel::from(default_stat));
    main_window.set_stat(stat.clone().into());

    let default_hamming_stats: Vec<HammingStats> = main_window.get_hamming_stats().iter().collect();
    let hamming_stats = Rc::new(slint::VecModel::from(default_hamming_stats));
    main_window.set_hamming_stats(hamming_stats.clone().into());

    let default_huffman_stats: Vec<HuffmanStats> = main_window.get_huffman_stats().iter().collect();
    let huffman_stats = Rc::new(slint::VecModel::from(default_huffman_stats));
    main_window.set_huffman_stats(huffman_stats.clone().into());

    let default_huffman_table: Vec<HuffmanEntry> = main_window.get_huffman_table().iter().collect();
    let huffman_table = Rc::new(slint::VecModel::from(default_huffman_table));
    main_window.set_huffman_table(huffman_table.clone().into());

    let errors_copy = errors.clone();
    let orig_copy = orig_text.clone();
    let proc_copy = proc_text.clone();
    main_window.global::<State>().on_protect(move |value| {
        handle_protect(value, errors_copy.clone(), &masks, orig_copy.clone(), proc_copy.clone() );
    });

    let errors_copy = errors.clone();
    let orig_copy = orig_text.clone();
    let proc_copy = proc_text.clone();
    main_window.global::<State>().on_desprotect(move |correct| {
        handle_desprotect(correct, errors_copy.clone(), &masks, orig_copy.clone(), proc_copy.clone());
    });

    let errors_copy = errors.clone();
    let orig_copy = orig_text.clone();
    let proc_copy = proc_text.clone();
    main_window
        .global::<State>()
        .on_corrupt(move |prob1, prob2| {
            handle_corrupt(errors_copy.clone(), orig_copy.clone(), proc_copy.clone(), prob1, prob2);
        });

    let errors_copy = errors.clone();
    let orig_copy = orig_text.clone();
    let proc_copy = proc_text.clone();
    main_window.global::<State>().on_compress(move || {
        handle_compress(errors_copy.clone(), orig_copy.clone(), proc_copy.clone());
    });

    let errors_copy = errors.clone();
    let orig_copy = orig_text.clone();
    let proc_copy = proc_text.clone();
    main_window.global::<State>().on_decompress(move || {
        handle_decompress(errors_copy.clone(), orig_copy.clone(), proc_copy.clone());
    });

    let orig_copy = orig_text.clone();
    let proc_copy = proc_text.clone();
    main_window
        .global::<State>()
        .on_choose_file(move |operation| match operation.to_string().as_str() {
            "show" => {
                if let Err(e) = handle_show_file(orig_copy.clone(), proc_copy.clone(), "".to_string(), Vec::new()) {
                    // TODO
                    println!("{}", e);
                }
            }
            "stats" => {
                if let Err(e) = handle_statistics(
                    stat.clone(),
                    hamming_stats.clone(),
                    huffman_stats.clone(),
                    huffman_table.clone(),
                ) {
                    // TODO handling
                    println!("{}", e);
                }
            }
            _ => return,
        });

    main_window.run().unwrap();
}

fn handle_protect(
    value: SharedString,
    errors: Rc<VecModel<SharedString>>,
    masks: &[[u8; MAX_BLOCK_SIZE]; MAX_EXPONENT], orig_text: Rc<VecModel<SharedString>>,
    proc_text: Rc<VecModel<SharedString>>)-> Result<(), std::io::Error> {
    let path = match choose_file(hamming::encoder::VALID_EXTENTIONS.into()) {
        Some(val) => val,
        None => return Ok(()),
    };

    let block_size = value.parse().unwrap();

    if let Err(e) = hamming::encoder::encode(&path, block_size, &masks) {
        errors.set_row_data(0, e.to_string().into());
    }

    println!("{}", path);

    if let Err(e) =  handle_show_file(orig_text.to_owned(), proc_text.to_owned(), path, hamming::encoder::EXTENTIONS.into()) {
                    // TODO
                    println!("{}", e);
    }

    Ok(())
}

fn handle_desprotect(
    correct: bool,
    errors: Rc<VecModel<SharedString>>,
    masks: &[[u8; MAX_BLOCK_SIZE]; MAX_EXPONENT], orig_text: Rc<VecModel<SharedString>>,
    proc_text: Rc<VecModel<SharedString>>)-> Result<(), std::io::Error>
{
    let path = match choose_file(hamming::decoder::VALID_EXTENTIONS.into()) {
        Some(val) => val,
        None => return Ok(()),
    };

    if let Err(e) = hamming::decoder::decode(&path, correct, masks) {
        errors.set_row_data(1, e.to_string().into());
    }

    let mut extension = Vec::new();
    println!("{}",path);
    if path.has_extention("HA1") || path.has_extention("HA2") || path.has_extention("HA3") {
        match path.chars().last().unwrap(){
                '1'=>extension.push("DC1"),
                '2'=>extension.push("DC2"),
                '3'=>extension.push("DC3"),
                _=>println!("Error de deteccion de archivo"),
        }
    }else{
        if correct {
            match path.chars().last().unwrap(){
                '1'=>extension.push("DC1"),
                '2'=>extension.push("DC2"),
                '3'=>extension.push("DC3"),
                _=>println!("Error de deteccion de archivo"),
            }
        }else{
            match path.chars().last().unwrap(){
                '1'=>extension.push("DE1"),
                '2'=>extension.push("DE2"),
                '3'=>extension.push("DE3"),
                _=>println!("Error de deteccion de archivo"),
            }
        }
    }

    if let Err(e) =  handle_show_file(orig_text.to_owned(), proc_text.to_owned(), path, extension) {
                    // TODO
                    println!("{}", e);
    }

    Ok(())
}

fn handle_corrupt(errors: Rc<VecModel<SharedString>>, orig_text: Rc<VecModel<SharedString>>,
    proc_text: Rc<VecModel<SharedString>>, prob1: f32, prob2: f32)-> Result<(), std::io::Error> {
    println!("Out: {}, {}", prob1, prob2);
    let path = match choose_file(hamming::noise::VALID_EXTENTIONS.into()) {
        Some(val) => val,
        None => return Ok(()),
    };

    // TODO siempre recibe probabilidades 0 de la GUI
    if let Err(e) = hamming::noise::corrupt(&path, 0.3, 0.1) {
        errors.set_row_data(2, e.to_string().into());
    }

    if let Err(e) =  handle_show_file(orig_text.to_owned(), proc_text.to_owned(), path, hamming::noise::EXTENTIONS.into()) {
                    // TODO
                    println!("{}", e);
    }

    Ok(())
}

fn handle_compress(errors: Rc<VecModel<SharedString>>, orig_text: Rc<VecModel<SharedString>>,
    proc_text: Rc<VecModel<SharedString>>)-> Result<(), std::io::Error> {
    let path = match choose_file(huffman::compress::VALID_EXTENTIONS.into()) {
        Some(val) => val,
        None => return Ok(()),
    };

    if let Err(e) = huffman::compress::compress(&path) {
        errors.set_row_data(3, e.to_string().into());
    }

    if let Err(e) =  handle_show_file(orig_text.to_owned(), proc_text.to_owned(), path, huffman::compress::EXTENTION.into()) {
                    // TODO
                    println!("{}", e);
    }

    Ok(())
}

fn handle_decompress(errors: Rc<VecModel<SharedString>>, orig_text: Rc<VecModel<SharedString>>,
    proc_text: Rc<VecModel<SharedString>>)-> Result<(), std::io::Error> {
    let path = match choose_file(huffman::decompress::VALID_EXTENTIONS.into()) {
        Some(val) => val,
        None => return Ok(()),
    };

    if let Err(e) = huffman::decompress::decompress(&path) {
        errors.set_row_data(4, e.to_string().into());
    }
    
    if let Err(e) =  handle_show_file(orig_text.to_owned(), proc_text.to_owned(), path, huffman::decompress::EXTENTION.into()) {
                    // TODO
                    println!("{}", e);
    }

    Ok(())
}

fn handle_show_file(
    orig_text: Rc<VecModel<SharedString>>,
    proc_text: Rc<VecModel<SharedString>>, mut path: String, mut valid_extentions: Vec<&str>
) -> Result<(), std::io::Error> {
    if path == ""{
        println!("entre");
        valid_extentions = ["dhu", "DE1", "DE2", "DE3", "DC1", "DC2", "DC3"].into();
        path = match choose_file(valid_extentions) {
            Some(val) => val,
            None => return Ok(()),
        };
    
    }

    let mut orig_extentions = hamming::encoder::VALID_EXTENTIONS.to_vec();
    orig_extentions.extend_from_slice(huffman::compress::VALID_EXTENTIONS.to_vec().as_slice());
    
    let mut new_orig_text: Vec<SharedString> = Vec::new();
    while let Some(ext) = orig_extentions.pop() {
        if let Ok(mut file) = File::open(&path.with_extention(ext)) {
            let mut buffer = Vec::new();

            file.read_to_end(&mut buffer)?;

            let contents = String::from_utf8_lossy(&buffer).to_string();

            new_orig_text.push(contents.into());
        }
    }

    let mut file = File::open(&path).unwrap();

    let mut new_proc_text: Vec<SharedString> = Vec::new();
    let mut buffer = Vec::new();

    file.read_to_end(&mut buffer)?;

    let contents = String::from_utf8_lossy(&buffer).to_string();

    new_proc_text.push(contents.into());
    new_proc_text.push("".into());

    proc_text.set_vec(new_proc_text);
    orig_text.set_vec(new_orig_text);

    Ok(())
}

fn handle_statistics(
    stat: Rc<VecModel<SharedString>>,
    hamming_stats: Rc<VecModel<HammingStats>>,
    huffman_stats: Rc<VecModel<HuffmanStats>>,
    huffman_table: Rc<VecModel<HuffmanEntry>>,
) -> Result<(), std::io::Error> {
    let valid_extentions = ["HA1", "HA2", "HA3", "HE1", "HE2", "HE3", "huf"].into();
    let hamming_extentions: Vec<&str> = ["HA1", "HA2", "HA3", "HE1", "HE2", "HE3"].into();

    let path = match choose_file(valid_extentions) {
        Some(val) => val,
        None => return Ok(()),
    };

    let mut new_stat: Vec<SharedString> = Vec::new();
    if let Some(index) = hamming_extentions
        .iter()
        .position(|&x| path.has_extention(x))
    {
        new_stat.push("hamming".into());
        stat.set_vec(new_stat);
        let block_size = hamming::BLOCK_SIZES[index % 3];
        let exponent = hamming::EXPONENTS[index % 3];

        let mut new_hamming_stats: Vec<HammingStats> = Vec::new();

        let mut file_size: u64 = 0;
        let mut n_blocks: u64 = 0;
        let mut reader = BufReader::new(File::open(path)?);

        read_u64(&mut reader, &mut n_blocks)?;
        read_u64(&mut reader, &mut file_size)?;

        let info_bits = file_size * 8;
        let protection_bits = n_blocks * (exponent as u64 + 1);
        let filler_bits = n_blocks * block_size as u64 - info_bits - protection_bits;

        new_hamming_stats.push(HammingStats {
            filler_bits: filler_bits as i32,
            info_bits: info_bits as i32,
            protection_bits: protection_bits as i32,
        });

        hamming_stats.set_vec(new_hamming_stats);
    } else {
        new_stat.push("huffman".into());
        stat.set_vec(new_stat);

        let mut new_huffman_stats: Vec<HuffmanStats> = Vec::new();
        let mut new_huffman_table: Vec<HuffmanEntry> = Vec::new();

        let mut info = huffman::get_info(&path)?;

        while let Some(TableEntry { orig, prob, code }) = info.table.pop() {
            new_huffman_table.push(HuffmanEntry {
                orig: orig.into(),
                code: code.into(),
                prob,
            });
        }

        new_huffman_stats.push(HuffmanStats {
            comp_size: info.comp_size as i32,
            orig_size: info.file_size as i32,
            table_size: info.table_size as i32,
        });

        huffman_stats.set_vec(new_huffman_stats);
        huffman_table.set_vec(new_huffman_table);
    }

    Ok(())
}

fn choose_file(valid_extentions: Vec<&str>) -> Option<String> {
    Some(
        FileDialog::new()
            .add_filter("", valid_extentions.as_ref())
            .set_directory(".")
            .pick_file()?
            .as_path()
            .to_str()
            .unwrap()
            .to_string(),
    )
}
